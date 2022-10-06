package rmit.sec.webstorepmicroservice.Transaction.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.Transaction.CustomObjects.EncryptedTransaction;
import rmit.sec.webstorepmicroservice.Transaction.CustomObjects.EncryptedViewTransactionRequest;
import rmit.sec.webstorepmicroservice.Transaction.requests.TransactionRequest;
import rmit.sec.webstorepmicroservice.Transaction.services.TransactionService;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;
import rmit.sec.webstorepmicroservice.utils.TypeConvertUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/authorised/transactions")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SessionKeyService sessionKeyService;
    @Autowired
    private TypeConvertUtil typeConvertUtil;
    @Autowired
    private JWTUtil jwtUtil;
    private final Logger logger = LogManager.getLogger(this.getClass());

    //Endpoint to retrieve a particular transaction
    @GetMapping("/getTransactionByID")
    public EncryptedTransaction getTransactionByID(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedViewTransactionRequest viewRequest){
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        Long transactionID = typeConvertUtil.convertToLong(sessionKeyService.aesDecryptMessage(sessionID, viewRequest.getTransactionID()));
        return transactionService.getTransaction(sessionID, userID, transactionID);
    }

    // Endpoint to get list of purchases for the user
    @GetMapping("/getAllPurchases")
    public List<EncryptedTransaction> getAllPurchasesByUserID(HttpServletRequest request, @RequestParam Long sessionID){
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        return transactionService.getAllPurchasesByUserID(sessionID, userID);
    }

    // Endpoint to get list of sales for the user
    @GetMapping("/getAllSales")
    public List<EncryptedTransaction> getAllSalesByUserID(HttpServletRequest request, @RequestParam Long sessionID){
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        return transactionService.getAllSalesByUserID(sessionID, userID);
    }

    // Endpoint to save a transaction
    @PostMapping("/saveTransaction")
    public String saveTransaction(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedTransaction transaction){
        String result = "";
        try{
            Long buyerID = typeConvertUtil.convertToLong(sessionKeyService.aesDecryptMessage(sessionID, transaction.getBuyerID()));
            Long sellerID = typeConvertUtil.convertToLong(sessionKeyService.aesDecryptMessage(sessionID, transaction.getSellerID()));
            Double totalCost = typeConvertUtil.convertToDouble(sessionKeyService.aesDecryptMessage(sessionID, transaction.getTotalCost()));

            TransactionRequest transactionRequest = new TransactionRequest(buyerID, sellerID, totalCost);
            result = transactionService.saveTransaction(transactionRequest);
        }catch (Exception e){
            logger.error("Error in decrypting transaction details");
            result = "Failed to save transaction";
        }
        return result;
    }

}
