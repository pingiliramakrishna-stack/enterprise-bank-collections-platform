package com.bank.collections.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;

public class PdfGenerationLambda
        implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final S3Client S3_CLIENT =
            S3Client.builder().build();

    private static final SnsClient SNS_CLIENT =
            SnsClient.builder().build();

    private static final String BUCKET_NAME =
            System.getenv("PDF_BUCKET_NAME");

    private static final String TOPIC_ARN =
            System.getenv("DOCUMENT_GENERATED_TOPIC_ARN");

    @Override
    public Map<String, Object> handleRequest(
            Map<String, Object> event,
            Context context) {

        context.getLogger().log(
                "PDF Generation Lambda invoked.");

        String workflowId =
                String.valueOf(event.get("workflowId"));

        String caseNumber =
                String.valueOf(event.get("caseNumber"));

        String customerId =
                String.valueOf(event.get("customerId"));

        String accountNumber =
                String.valueOf(event.get("accountNumber"));

        context.getLogger().log(
                "Generating PDF for Workflow ID: "
                        + workflowId);

        try {

            // -------------------------------------------------
            // 1. Generate PDF
            // -------------------------------------------------

            byte[] pdfBytes =
                    generatePdf(
                            workflowId,
                            caseNumber,
                            customerId,
                            accountNumber);

            // -------------------------------------------------
            // 2. Generate Document ID
            // -------------------------------------------------

            String documentId =
                    "DOC-" + UUID.randomUUID();

            // -------------------------------------------------
            // 3. Create S3 object key
            // -------------------------------------------------

            String s3Key =
                    "documents/"
                            + workflowId
                            + ".pdf";

            // -------------------------------------------------
            // 4. Upload PDF to S3
            // -------------------------------------------------

            uploadToS3(
                    pdfBytes,
                    s3Key);

            context.getLogger().log(
                    "PDF uploaded successfully. "
                            + "S3 Key: "
                            + s3Key);

            // -------------------------------------------------
            // 5. Create SNS notification
            // -------------------------------------------------

            String notification =
                    String.format("""
                            {
                              "workflowId": "%s",
                              "documentId": "%s",
                              "s3Key": "%s",
                              "status": "GENERATED"
                            }
                            """,
                            workflowId,
                            documentId,
                            s3Key);

            context.getLogger().log(
                    "Publishing document notification to SNS.");

            // -------------------------------------------------
            // 6. Publish notification to SNS
            // -------------------------------------------------

            PublishRequest publishRequest =
                    PublishRequest.builder()
                            .topicArn(TOPIC_ARN)
                            .message(notification)
                            .build();

            PublishResponse publishResponse =
                    SNS_CLIENT.publish(publishRequest);

            context.getLogger().log(
                    "SNS notification published successfully. "
                            + "MessageId: "
                            + publishResponse.messageId());

            // -------------------------------------------------
            // 7. Return successful response
            // -------------------------------------------------

            return Map.of(
                    "status",
                    "SUCCESS",

                    "message",
                    "PDF generated and notification published",

                    "workflowId",
                    workflowId,

                    "documentId",
                    documentId,

                    "s3Key",
                    s3Key
            );

        } catch (Exception ex) {

            context.getLogger().log(
                    "PDF generation failed: "
                            + ex.getMessage());

            throw new RuntimeException(
                    "PDF generation failed",
                    ex);
        }
    }

    // =========================================================
    // PDF Generation
    // =========================================================

    private byte[] generatePdf(
            String workflowId,
            String caseNumber,
            String customerId,
            String accountNumber)
            throws Exception {

        try (
                PDDocument document =
                        new PDDocument();

                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {

            PDPage page =
                    new PDPage();

            document.addPage(page);

            try (
                    PDPageContentStream contentStream =
                            new PDPageContentStream(
                                    document,
                                    page)
            ) {

                PDType1Font font =
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA);

                contentStream.beginText();

                contentStream.setFont(
                        font,
                        12);

                contentStream.newLineAtOffset(
                        50,
                        700);

                contentStream.showText(
                        "Bank Collections Document");

                contentStream.newLineAtOffset(
                        0,
                        -30);

                contentStream.showText(
                        "Workflow ID: "
                                + workflowId);

                contentStream.newLineAtOffset(
                        0,
                        -20);

                contentStream.showText(
                        "Case Number: "
                                + caseNumber);

                contentStream.newLineAtOffset(
                        0,
                        -20);

                contentStream.showText(
                        "Customer ID: "
                                + customerId);

                contentStream.newLineAtOffset(
                        0,
                        -20);

                contentStream.showText(
                        "Account Number: "
                                + accountNumber);

                contentStream.newLineAtOffset(
                        0,
                        -40);

                contentStream.showText(
                        "This document was generated "
                                + "by the Collections Workflow System.");

                contentStream.endText();
            }

            document.save(outputStream);

            return outputStream.toByteArray();
        }
    }

    // =========================================================
    // S3 Upload
    // =========================================================

    private void uploadToS3(
            byte[] pdfBytes,
            String s3Key) {

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(s3Key)
                        .contentType("application/pdf")
                        .build();

        S3_CLIENT.putObject(
                putObjectRequest,
                RequestBody.fromBytes(pdfBytes));
    }
}