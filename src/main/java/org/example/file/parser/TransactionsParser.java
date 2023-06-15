package org.example.file.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsParser {
    private List<TransactionParser> articleList;
}
