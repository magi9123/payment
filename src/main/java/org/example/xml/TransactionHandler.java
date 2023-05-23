package org.example.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.xml.ElementXml.*;

public class TransactionHandler extends DefaultHandler {

    private TransactionsXml transactionsXml;
    private TransactionXml transactionXml;

    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        transactionsXml = new TransactionsXml();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case TRANSACTIONS:
                transactionsXml.setArticleList(new ArrayList<>());
                break;
            case TRANSACTION:
                transactionXml = new TransactionXml();
                transactionsXml.getArticleList().add(transactionXml);
                break;
            case CUSTOMER:
                transactionXml.setCustomer(new CustomerXml());
                break;
            case MERCHANT:
                transactionXml.setMerchant(new MerchantXml());
                break;
            case AMOUNT:
            case TYPE:
            case CUSTOMER_EMAIL:
            case PHONE:
            case NAME:
            case DESCRIPTION:
            case MERCHANT_EMAIL:
            case STATUS:
            case BANK_ACCOUNT_SUM_CUSTOMER:
            case BANK_ACCOUNT_SUM_MERCHANT:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case AMOUNT:
                latestArticle().setAmount(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
            case TYPE:
                latestArticle().setType(Integer.parseInt(elementValue.toString()));
                break;
            case CUSTOMER_EMAIL:
                latestArticle().getCustomer().setEmail(elementValue.toString());
                break;
            case PHONE:
                latestArticle().getCustomer().setPhone(elementValue.toString());
                break;
            case NAME:
                latestArticle().getMerchant().setName(elementValue.toString());
                break;
            case DESCRIPTION:
                latestArticle().getMerchant().setDescription(elementValue.toString());
                break;
            case MERCHANT_EMAIL:
                latestArticle().getMerchant().setEmail(elementValue.toString());
                break;
            case STATUS:
                latestArticle().getMerchant().setStatus(Integer.parseInt(elementValue.toString()));
                break;
            case BANK_ACCOUNT_SUM_CUSTOMER:
                latestArticle().getCustomer().setBankAccountSum(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
            case BANK_ACCOUNT_SUM_MERCHANT:
                latestArticle().getMerchant().setBankAccountSum(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
        }
    }

    private TransactionXml latestArticle() {
        List<TransactionXml> articleList = transactionsXml.getArticleList();
        int latestArticleIndex = articleList.size() - 1;
        return articleList.get(latestArticleIndex);
    }
}
