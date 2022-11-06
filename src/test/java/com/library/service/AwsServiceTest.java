package com.library.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AwsServiceTest {
    @InjectMocks
    private AwsService awsServiceUnderTest;
    @Mock
    private AmazonS3 mockAmazonS3Client;
    @Mock
    private S3Object mockS3Object;

    @Test
    void testFetchFromS3() throws Exception {
        // Setup
        lenient().when(mockAmazonS3Client.getObject("test", "test")).thenReturn(mockS3Object);
        lenient().when(mockS3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(new ByteArrayInputStream("test".getBytes()), null));

        // Run the test
        final String result = awsServiceUnderTest.fetchFromS3("test", "test");

        // Verify the results
        assertEquals("test", result);
    }

    @Test
    void testFetchFromS3_GetObjectThrowsException() throws Exception {

        // Run the test
        Exception exception = assertThrows(Exception.class, () -> awsServiceUnderTest.fetchFromS3("test", null));

        //Verify the result
        assertTrue(exception instanceof AmazonS3Exception);
    }

    @Test
    void testFetchFromS3_GetObjectContentThrowsException() throws Exception {
        //Setup
        lenient().when(mockAmazonS3Client.getObject("test", "test")).thenReturn(mockS3Object);
        lenient().when(mockS3Object.getObjectContent()).thenReturn(null);

        // Run the test
        Exception exception = assertThrows(Exception.class, () -> awsServiceUnderTest.fetchFromS3("test", "test"));

        //Verify the result
        assertTrue(exception instanceof AmazonS3Exception);
    }

    @Test
    void testSaveToS3() throws Exception {

        // Run the test
        assertDoesNotThrow(() -> awsServiceUnderTest.saveToS3("test", "test", "test"));

        // Verify the results
        verify(mockAmazonS3Client).putObject(anyString(), anyString(), any(), any());
    }

    @Test
    void testSaveToS3_ThrowsException() {
        lenient().when(mockAmazonS3Client.putObject(anyString(), anyString(), any(), any())).thenThrow(AmazonS3Exception.class);

        // Run the test
        Exception exception = assertThrows(Exception.class, () -> awsServiceUnderTest.fetchFromS3("test", "test"));

        //Verify the result
        assertTrue(exception instanceof AmazonS3Exception);
    }
}
