package com.log.service;

import com.log.model.Log;
import com.log.repository.LogRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LogService  {

    private static  final String LOG_NAO_ENCONTRADO ="Log de não Encontrado";
    @Autowired
    LogRepository logRepository;

    List<Log> listaLog = new ArrayList<Log>();

    public void writeArquive(MultipartFile uploadfile) throws IOException {
        File file =  convertMultPartFileToFile(uploadfile, "arquivo");
        Path path = Paths.get(file.getPath());
        Files.lines(path).forEach(l->{
            convertLog(split(l));

        });
        logRepository.saveAll(listaLog);

    }


    public  File convertMultPartFileToFile(MultipartFile uploadfile, String name) throws IOException {

        File fileToSave = new File(name);
        fileToSave.createNewFile();
        FileOutputStream fos = new FileOutputStream(fileToSave);
        fos.write(uploadfile.getBytes());
        fos.close();
        return fileToSave;
    }

    public  List<String> split(String str){
        return Stream.of(str.split(Pattern.quote("|")))
                .map (String::new)
                .collect(Collectors.toList());
    }


    public  void convertLog(List<String> lista)  {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        Log log =new Log();
        log.setDate(LocalDateTime.parse(lista.get(0), format));
        log.setIp(lista.get(1).replace(".","").trim());
        log.setRequest(lista.get(2).replace("\"", ""));
        log.setStatus(Long.parseLong(lista.get(3)));
        log.setUserAgent(lista.get(4).replace("\"", ""));
        listaLog.add(log);

    }

    public List<Log> getAll() {
        return logRepository.findAll();
    }

    public Log findById(Long id) {
        Optional<Log> obj = logRepository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(LOG_NAO_ENCONTRADO
                        , Log.class.getName()));
    }


    public Log insert(Log obj) {
        return logRepository.save(obj);
    }


    public List<Log> findByIp(String ip) {
        Optional<List<Log>> obj = logRepository.findByIpOrderByDate(ip);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(LOG_NAO_ENCONTRADO
                        , Log.class.getName()));
    }


    public void excluir(Long id) {
        findById(id);
                  logRepository.deleteById(id);

    }

    public Log alterar(Log obj) {
        return logRepository.save(obj);
    }

}
