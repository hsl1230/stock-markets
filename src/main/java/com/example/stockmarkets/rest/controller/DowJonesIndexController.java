package com.example.stockmarkets.rest.controller;

import com.example.stockmarkets.document.DowJonesIndex;
import com.example.stockmarkets.message.ErrorInfo;
import com.example.stockmarkets.message.Message;
import com.example.stockmarkets.message.Response;
import com.example.stockmarkets.service.CsvFileService;
import com.example.stockmarkets.service.DowJonesIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Api(value = "Dow jones Indices")
@RestController
@RequestMapping("/api/dow-jones-indexes")
public class DowJonesIndexController {
    private CsvFileService csvFileService;
    private DowJonesIndexService dowJonesIndexService;

    @Autowired
    public DowJonesIndexController(CsvFileService csvFileService, DowJonesIndexService dowJonesIndexService) {
        this.csvFileService = csvFileService;
        this.dowJonesIndexService = dowJonesIndexService;
    }

    @ApiOperation(value = "upload dow jones indices and save them into mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "bad request")
    })
    @PostMapping(value="upload/single-csv")
    public ResponseEntity<Response> loadIndexes(@RequestParam("csvfile") MultipartFile csvfile) {
        Response response = new Response();

        if (csvfile.getOriginalFilename().isEmpty()) {
            response.addMessage(new Message(csvfile.getOriginalFilename(),
                    "No selected file to upload! Please do the checking", "fail"));

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            csvFileService.store(csvfile.getInputStream());
            response.addMessage(new Message(csvfile.getOriginalFilename(), "Upload File Successfully!", "ok"));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.addMessage(new Message(csvfile.getOriginalFilename(), e.getMessage(), "fail"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "create a dow jones index and save it into mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 500, message = "internal server error")
    })
    @PostMapping("")
    public ResponseEntity<Response> createIndex(@RequestBody DowJonesIndex dowJonesIndex, HttpServletRequest request) {
        try {
            // save to MongoDB database
            DowJonesIndex _customer = dowJonesIndexService.saveDowJonesIndex(dowJonesIndex);

            String message = "Upload Successfully a Customer to MongoDB with id = " + _customer.getId();
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    List.of(_customer)), HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Can NOT upload  a Customer to MongoDB database";
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    new ErrorInfo(e.getMessage(), "fail")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "find all dow jones indices of a stock from mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 500, message = "internal server error")
    })
    @GetMapping("")
    public ResponseEntity<Response> getIndicesByStock(@RequestParam("stock") String stock, HttpServletRequest request) {
        try {
            List<DowJonesIndex> _customer = dowJonesIndexService.findDowJonesIndexByStock(stock);

            String message = "query by stock Successfully";
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    _customer), HttpStatus.OK);
        } catch (Exception e) {
            String message = "Can NOT get dow jones indices from MongoDB database";
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    new ErrorInfo(e.getMessage(), "fail")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "delete the dow jones index from mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 500, message = "internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteIndex(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            Optional<DowJonesIndex> dowJonesIndex = dowJonesIndexService.deleteDowJonesIndex(id);

            String message = "delete Successfully";
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    dowJonesIndex.stream().toList()), HttpStatus.OK);
        } catch (Exception e) {
            String message = "Can NOT delete the dow jones indices from MongoDB database";
            return new ResponseEntity<Response>(new Response(message, request.getRequestURI(),
                    new ErrorInfo(e.getMessage(), "fail")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
