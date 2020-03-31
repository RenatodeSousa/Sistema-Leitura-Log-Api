package com.log.controller;
import com.log.model.Log;
import com.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/log")
public class logController {

    @Autowired
    private LogService service;

    @PostMapping
    public void logImport(@RequestParam("file") MultipartFile uploadfile) throws IOException {
             this.service.writeArquive(uploadfile);
    }

    @GetMapping
    public ResponseEntity<List<Log>> getAll() {
        List<Log> listLog = service.getAll();
        return ResponseEntity.ok(listLog);
    }

    @GetMapping("{id}")
    public ResponseEntity<Log> findById(@PathVariable("id") String id) {
        Log log = service.findById(Long.parseLong(id));
        return ResponseEntity.ok(log);
    }

    @GetMapping("ip/{ip}")
    public ResponseEntity<List<Log>> findByIp(@PathVariable("ip") String ip) {
        List<Log> log = service.findByIp(ip);
        return ResponseEntity.ok(log);
    }


    @PostMapping("/insert")
    public ResponseEntity<Void> insert(@RequestBody Log obj) {
        System.out.println(obj.getDate());
        obj = service.insert(obj);
        ResponseEntity.ok(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("{id}")
    public ResponseEntity<Void> alterar(@RequestBody Log obj,
                                        @PathVariable Long id) {
        obj.setId(id);
        obj = service.alterar(obj);
        ResponseEntity.ok(obj);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

 }
