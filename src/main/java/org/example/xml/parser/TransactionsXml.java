package org.example.xml.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsXml {
    private List<TransactionXml> articleList;
}
