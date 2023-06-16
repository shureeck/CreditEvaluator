package org.example.core.dao;

import lombok.extern.log4j.Log4j2;
import org.example.core.utils.LoggerMessages;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


@Component
@Log4j2
public class SegmentsDao implements Dao {
    private static final ResourceBundle resources = ResourceBundle.getBundle("constants");

    @Override
    public long getCreditModifier(long id) throws RuntimeException {
        try {
            Class.forName(resources.getString("jdbcClass"));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            log.error(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n\t")));
        }
        String database = resources.getString("database");
        log.info(LoggerMessages.getMessage("SegmentsDao.getCreditModifier.connect", database));
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database);
             Statement statement = connection.createStatement()) {
            String query = String.format(resources.getString("selectCreditModifier"), id);
            log.info(LoggerMessages.getMessage("SegmentsDao.getCreditModifier.query", query));
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                long result = resultSet.getLong(1);
                log.info(LoggerMessages.getMessage("SegmentsDao.getCreditModifier.result", String.valueOf(result)));
                return result;
            } else {
                log.error(LoggerMessages.getMessage("SegmentsDao.getCreditModifier.notfound", String.valueOf(id)));
                throw new RuntimeException(LoggerMessages.getMessage("SegmentsDao.getCreditModifier.notfound",
                        String.valueOf(id)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n\t")));
            throw new RuntimeException(e.getMessage());
        }
    }
}
