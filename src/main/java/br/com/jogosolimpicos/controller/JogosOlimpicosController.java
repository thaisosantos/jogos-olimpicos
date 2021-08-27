package br.com.jogosolimpicos.controller;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.exceptions.ApiErrors;
import br.com.jogosolimpicos.exceptions.Response400Exception;
import br.com.jogosolimpicos.exceptions.Response404Exception;
import br.com.jogosolimpicos.facade.CompeticaoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static br.com.jogosolimpicos.utils.ValidatorUtil.validaDatas;

@RestController
@RequestMapping("/api/competicao")
public class JogosOlimpicosController {

    @Autowired
    private CompeticaoFacade competicaoFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompeticaoDTO> cadastrar(@RequestBody @Valid final CompeticaoDTO competicaoDTO){
        validaDatas(competicaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoFacade.cadastrar(competicaoDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CompeticaoDTO>> buscaPorModalidade(@RequestParam(value = "modalidade",required = false) final String modalidade){
       return ResponseEntity.ok(competicaoFacade.buscarCompeticoes(modalidade));
    }

    @GetMapping("/clima")
    public ResponseEntity<ClimaDTO> consultaClima(){
        return ResponseEntity.ok(competicaoFacade.consultaClima());
    }

    /*
    * Handler para tratar erros na validacao dos campos da requisicao
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    /*
     * Handler para tratar erros 400
     * */
    @ExceptionHandler(Response400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(Response400Exception ex){
        return new ApiErrors(ex);
    }

    /*
     * Handler para tratar erros 404
     * */
    @ExceptionHandler(Response404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleRespose404Exception(Response404Exception ex){
        return new ApiErrors(ex);
    }
}
