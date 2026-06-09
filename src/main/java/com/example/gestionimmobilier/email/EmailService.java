package com.example.gestionimmobilier.email;

import com.example.gestionimmobilier.Entity.Contrat;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Async
    public void envoyerLienSignature(
            String to,
            String clientNom,
            Contrat contrat,
            String lienSignature
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("clientNom", clientNom);
        properties.put("lienSignature", lienSignature);
        properties.put("bienTitre", contrat.getBien() != null ? contrat.getBien().getTitre() : "N/A");
        properties.put("montantLoyer", contrat.getMontantLoyer());
        properties.put("caution", contrat.getCaution());
        properties.put("dateDebut", contrat.getDateDebut() != null ? sdf.format(contrat.getDateDebut()) : "N/A");
        properties.put("dateFin", contrat.getDateFin() != null ? sdf.format(contrat.getDateFin()) : "N/A");

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("support@gestionimmobilier.tn");
        helper.setTo(to);
        helper.setSubject("Votre contrat de location est prêt à signer");

        String template = templateEngine.process(
                EmailTemplateName.CONTRAT_SIGNATURE.getName(),
                (IContext) context
        );
        helper.setText(template, true);

        mailSender.send(mimeMessage);
        log.info("Email signature envoyé à {}", to);
    }

    @Async
    public void envoyerConfirmationSignature(
            String to,
            String proprietaireNom,
            Contrat contrat
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("proprietaireNom", proprietaireNom);
        properties.put("clientNom", contrat.getClient() != null ? contrat.getClient().getNom() : "N/A");
        properties.put("bienTitre", contrat.getBien() != null ? contrat.getBien().getTitre() : "N/A");
        properties.put("montantLoyer", contrat.getMontantLoyer());
        properties.put("dateDebut", contrat.getDateDebut() != null ? sdf.format(contrat.getDateDebut()) : "N/A");
        properties.put("dateFin", contrat.getDateFin() != null ? sdf.format(contrat.getDateFin()) : "N/A");

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("support@gestionimmobilier.tn");
        helper.setTo(to);
        helper.setSubject("Contrat signé par votre client");

        String template = templateEngine.process(
                EmailTemplateName.CONTRAT_SIGNE.getName(),
                (IContext) context
        );
        helper.setText(template, true);

        mailSender.send(mimeMessage);
        log.info("Email confirmation envoyé au propriétaire {}", to);
    }
}