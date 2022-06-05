package com.smartprospect.smartprospect.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smartprospect.smartprospect.company.Company;
import com.smartprospect.smartprospect.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import javax.ejb.Schedule;
import javax.swing.text.html.HTML;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyScraper {
    private final CompanyService companyService;

    /**
     * Cette methode permet de faire le scraping d'un site spécifiquequi est " Le Portail de l'Industrie Tunisienne ", plus précisément les informations
     * des entreprises certifiéesen Tunisie disponibles à traver l'url suivant : http://www.tunisieindustrie.nat.tn/fr/certifdbi.asp?action=list&idsect=&pagenum=1
     */
    @Schedule(dayOfMonth = "First", hour = "2")
    public void ScrapeIndustriesPIT() {
        final String url = "http://www.tunisieindustrie.nat.tn/fr/certifdbi.asp?action=list&idsect=&pagenum=";
        for (int i = 1; i<=52; i++) {
            try (final WebClient webClient = new WebClient()) {
                HtmlPage homePage = webClient.getPage(url+i);
                HtmlElement homePageDOM = homePage.getDocumentElement();
                List<HtmlElement> homePageCompaniesList = homePageDOM.getElementsByAttribute("tr", "onmouseover", "this.style.backgroundColor='#F1BFBC'");
                Iterator<HtmlElement> homeIterator = homePageCompaniesList.iterator();
                while (homeIterator.hasNext()) {
                    HtmlPage companyInfoPage = homeIterator.next().click();
                    HtmlElement companyInfoPageDOM = companyInfoPage.getDocumentElement();
                    List<HtmlElement> companyInfoList = companyInfoPageDOM.getElementsByAttribute("td", "class", "last");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    Company company = new Company(companyInfoList.get(0).asNormalizedText(), companyInfoList.get(1).asNormalizedText(),
                            companyInfoList.get(2).asNormalizedText().replace(" - "," | "), companyInfoList.get(3).asNormalizedText(),
                            companyInfoList.get(4).asNormalizedText(), companyInfoList.get(5).asNormalizedText(), companyInfoList.get(6).asNormalizedText(),
                            companyInfoList.get(7).asNormalizedText(), companyInfoList.get(8).asNormalizedText(), companyInfoList.get(9).asNormalizedText(),
                            companyInfoList.get(10).asNormalizedText(), companyInfoList.get(11).asNormalizedText(), companyInfoList.get(12).asNormalizedText(),
                            companyInfoList.get(13).asNormalizedText(), companyInfoList.get(14).asNormalizedText(),
                            LocalDate.parse(companyInfoList.get(15).asNormalizedText(),formatter),
                            Double.valueOf(String.valueOf(companyInfoList.get(16).asNormalizedText().equals("") ? 0 : companyInfoList.get(16).asNormalizedText().replace(" ",""))),
                            Integer.parseInt(companyInfoList.get(17).asNormalizedText().replace(" ","")), "Industrie");
                    companyService.addNew(company);
                    //La ligne suivante consiste à retourner vers la page où se trouvent la liste des entreprises
                    //sans quoi nous n'aurions que les informations de la première entreprise en boucle
                    webClient.getCurrentWindow().getHistory().back();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void ScrapeServicesPIT() {
        try (final WebClient webClient = new WebClient()) {
            final String url = "http://www.tunisieindustrie.nat.tn/fr/dbs.asp";
            HtmlPage searchPage = webClient.getPage(url);
            HtmlElement searchPageDom = searchPage.getDocumentElement();
            HtmlElement secteurSelect = searchPageDom.getElementsByAttribute("select","name","secteur").get(0);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        final String url = "http://www.tunisieindustrie.nat.tn/fr/dbs.asp?action=search&pagenum=";
        for (int i = 1; i<=56; i++) {
            try (final WebClient webClient = new WebClient()) {
                HtmlPage homePage = webClient.getPage(url+i);
                HtmlElement homePageDOM = homePage.getDocumentElement();
                List<HtmlElement> homePageCompaniesList = homePageDOM.getElementsByAttribute("tr", "onmouseover", "this.style.backgroundColor='#F1BFBC'");
                Iterator<HtmlElement> homeIterator = homePageCompaniesList.iterator();
                while (homeIterator.hasNext()) {
                    HtmlPage companyInfoPage = homeIterator.next().click();
                    HtmlElement companyInfoPageDOM = companyInfoPage.getDocumentElement();
                    List<HtmlElement> companyInfoList = companyInfoPageDOM.getElementsByAttribute("td", "class", "last");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

                    Company company = new Company(companyInfoList.get(0).asNormalizedText(),
                            companyInfoList.get(1).asNormalizedText(),
                            null,
                            companyInfoList.get(2).asNormalizedText(),
                            companyInfoList.get(3).asNormalizedText(),
                            companyInfoList.get(4).asNormalizedText(),
                            companyInfoList.get(5).asNormalizedText(),
                            companyInfoList.get(6).asNormalizedText(),
                            null,
                            companyInfoList.get(7).asNormalizedText(),
                            companyInfoList.get(8).asNormalizedText(),
                            companyInfoList.get(9).asNormalizedText(),
                            companyInfoList.get(10).asNormalizedText(),
                            companyInfoList.get(11).asNormalizedText(),
                            companyInfoList.get(12).asNormalizedText(),
                            LocalDate.parse(companyInfoList.get(13).asNormalizedText(),formatter),
                            Double.valueOf(String.valueOf(companyInfoList.get(16).asNormalizedText().equals("") ? 0 : companyInfoList.get(16).asNormalizedText().replace(" ",""))),
                            Integer.parseInt(companyInfoList.get(17).asNormalizedText().replace(" ","")),
                            "Service"
                    );
                    companyService.addNew(company);

                    //La ligne suivante consiste à retourner vers la page où se trouvent la liste des entreprises
                    //sans quoi nous n'aurions que les informations de la première entreprise en boucle
                    webClient.getCurrentWindow().getHistory().back();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
