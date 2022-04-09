package ru.bagration.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bagration.spring.entity.ErrorMessage;
import ru.bagration.spring.entity.Theme;
import ru.bagration.spring.repository.ErrorMessageRepository;
import ru.bagration.spring.repository.ThemeRepository;

import java.util.UUID;


@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(BlogApplication.class, args);

        var error_message_rep = context.getBean(ErrorMessageRepository.class);

        /*var error_message1 = new ErrorMessage();
        error_message1.setKey("publication.content.require.not.null");
        error_message1.setValue("В статье должно быть содержание");
        error_message1.setLang("ru");

        var error_message2 = new ErrorMessage();
        error_message2.setKey("publication.content.require.not.empty");
        error_message2.setValue("Содержание статьи не может быть пустой строкой");
        error_message2.setLang("ru");

        var error_message3 = new ErrorMessage();
        error_message3.setKey("publication.content.require.not.blank");
        error_message3.setValue("Содержание статьи не может быть пустыми пробелами");
        error_message3.setLang("ru");*/


        var error_message1 = new ErrorMessage();
        error_message1.setKey("publication.content.require.not.null");
        error_message1.setValue("Article must have a content");
        error_message1.setLang("en");

        var error_message2 = new ErrorMessage();
        error_message2.setKey("publication.content.require.not.empty");
        error_message2.setValue("Publication title shouldn't be empty");
        error_message2.setLang("en");

        var error_message3 = new ErrorMessage();
        error_message3.setKey("publication.content.require.not.blank");
        error_message3.setValue("Publication content shouldn't be empty spaces");
        error_message3.setLang("en");


        error_message_rep.save(error_message1);
        error_message_rep.save(error_message2);
        error_message_rep.save(error_message3);

    }


}
