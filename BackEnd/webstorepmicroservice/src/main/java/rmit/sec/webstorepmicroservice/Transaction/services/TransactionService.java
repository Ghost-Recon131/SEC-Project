package rmit.sec.webstorepmicroservice.Transaction.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.Transaction.CustomObjects.EncryptedTransaction;
import rmit.sec.webstorepmicroservice.Transaction.model.Transactions;
import rmit.sec.webstorepmicroservice.Transaction.repository.TransactionRepository;
import rmit.sec.webstorepmicroservice.Transaction.requests.TransactionRequest;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SessionKeyService sessionKeyService;
    @Autowired
    private EncryptionUtil encryptionUtil;
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get a single transaction
    public EncryptedTransaction getTransaction(Long sessionID, Long userID, Long transactionID){
        List<Transactions> tmpList = new ArrayList<>();
        EncryptedTransaction encryptedTransaction = null;
        Transactions transaction = transactionRepository.getByTransactionID(transactionID);

        // Check that the user is related to the transaction before allowing them to view it
        if(transaction.getSellerID().equals(userID) || transaction.getBuyerID().equals(userID)) {
            tmpList.add(transaction);
            encryptedTransaction = getEncryptedTransaction(sessionID, tmpList).get(0);
        }
        return encryptedTransaction;
    }

    // Get all purchases for a user
    public List<EncryptedTransaction> getAllPurchasesByUserID(Long sessionID, Long userID) {
        List<Transactions> transactions = transactionRepository.getAllByBuyerID(userID);
        return getEncryptedTransaction(sessionID, transactions);
    }

    // Get all sales for a user
    public List<EncryptedTransaction> getAllSalesByUserID(Long sessionID, Long userID) {
        List<Transactions> transactions = transactionRepository.getAllBySellerID(userID);
        return getEncryptedTransaction(sessionID, transactions);
    }

    // Save a successful transaction
    public String saveTransaction(TransactionRequest request){
        String result = "";
        try{
            Transactions transaction = new Transactions(request.getItemID(), request.getSellerID(), request.getBuyerID(), request.getTotalCost());
            transactionRepository.save(transaction);
            result = "Transaction saved successfully";
        }catch (Exception e){
            logger.error("Failed to save transaction: \n" + e.getMessage());
            result = "Failed to save transaction";
        }
        return result;
    }

    // Method to create encrypted transaction objects to return to frontend
    private List<EncryptedTransaction> getEncryptedTransaction(Long sessionID, List<Transactions> transactionsList){
        List<EncryptedTransaction> encryptedTransactions = new ArrayList<>();
        try{
            String sessionKey = sessionKeyService.getAESKey(sessionID);
            // Encrypt the original transactions objects and add to new arraylist
            for (Transactions transaction : transactionsList) {
                EncryptedTransaction encryptedTransaction = new EncryptedTransaction(
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getTransactionID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getItemID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getBuyerID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getSellerID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getTotalCost().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, transaction.getCreate_At().toString())
                );
                encryptedTransactions.add(encryptedTransaction);
            }
        }catch (Exception e){
            logger.error("Error returning list of all items \n");
            logger.error(e.getMessage());
        }
        return encryptedTransactions;
    }

}
