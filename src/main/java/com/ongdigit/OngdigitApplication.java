package com.ongdigit;

import com.ongdigit.entities.Place;
import com.ongdigit.file.StorageProperties;
import com.ongdigit.restServices.PlaceRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class OngdigitApplication implements CommandLineRunner {

    @Autowired
    private PlaceRestService placeRestService;
    public static void main(String[] args) {
        SpringApplication.run(OngdigitApplication.class, args);
    }

    @Override
    public void run(String... args) {
        placeRestService.placeList().forEach(
                place -> {
                    try {
                        changeAccess(place);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    //tâche planifiée qui chaque minute de 8h à 18h chaque jour
    @Scheduled(cron = "0 * 8-18 * * ?")
    public void fixedDelaySch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        String text = "Fixed Delay scheduler:: " + strDate;
        System.out.println(text);
        List<Place> placeList = placeRestService.placeList();
        //System.out.println("placeid" + placeList);
        placeList.forEach(
                place -> {
                    try {
                        changeAccess(place);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void changeAccess(Place place) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date datePlace = sdf.parse(place.getDateReservation());
        int hourPlace = Integer.parseInt(place.getTimeReservation().split(",")[1]);
        Date now = new Date();
//        System.out.println("ici " + place.getId());
//        System.out.println("ici " + place.getDateReservation());
//        System.out.println("ici " + compareDate(datePlace, now));
        switch (compareDate(datePlace, now)){
            case 1:
                place.setAccess(false);
                placeRestService.update(place.getId(), place);
                break;
            case 0:
                if (hourPlace < now.getHours()) {
//                    System.out.println("ici0 " + place.getId());
//                    System.out.println("ici0 " + place.getComputerNumber());
                    place.setAccess(false);
                    System.out.println(placeRestService.update(place.getId(), place));
                }
                break;
        }
    }

    int compareDate(Date datePlace, Date now){

        int check = 2;

        if (datePlace.getDate() == now.getDate() && datePlace.getMonth() == now.getMonth() && datePlace.getYear() == now.getYear()){
            check = 0;
        }
        else if ((datePlace.getDate() < now.getDate() && datePlace.getMonth() == now.getMonth() && datePlace.getYear() == now.getYear()) || (datePlace.getMonth() < now.getMonth() && datePlace.getYear() == now.getYear()) || datePlace.getYear() < now.getYear()){
            check = 1;
        }

        return check;
    }
}
