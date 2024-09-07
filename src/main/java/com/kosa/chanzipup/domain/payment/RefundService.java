package com.kosa.chanzipup.domain.payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final IamportClient iamportClient;

    public void refundBy(String impUid) {
        CancelData cancelData = new CancelData(impUid, true);
        try {
            IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
            System.out.println(response.getResponse());
            System.out.println(response.getMessage());
            System.out.println(response.getCode());
        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
