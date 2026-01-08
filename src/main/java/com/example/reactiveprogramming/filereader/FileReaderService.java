package com.example.reactiveprogramming.filereader;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.isNull;

public class FileReaderService {

    public Flux<String> read(Path path){
        return Flux.generate(()-> openFile(path),this::readFile);
    }


    private BufferedReader openFile(Path path) throws IOException {
        return Files.newBufferedReader(path);
    }


    private BufferedReader readFile(BufferedReader bufferedReader, SynchronousSink<String> sink)  {

        try {
            var line =  bufferedReader.readLine();

            if(isNull(line)){
                sink.complete();
            }
            else{
                sink.next(bufferedReader.readLine());
            }
        } catch (IOException e) {
            sink.error(e);
        }
        return bufferedReader;
    }
}
