package br.univille.apidacs2022.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.apidacs2022.service.ProcedimentoRealizadoService;
import br.univille.apidacs2022.service.ProcedimentoService;
import br.univille.coredacs2022.entity.Procedimento;
import br.univille.coredacs2022.entity.ProcedimentoRealizado;

@RestController
@RequestMapping("/api/v1/procedimentos/realizados")
public class ProcedimentoRealizadoControllerAPI {
    @Autowired
    private ProcedimentoRealizadoService service;
    @Autowired
    private ProcedimentoService procedimentoService;

    @GetMapping
    public ResponseEntity<List<ProcedimentoRealizado>> getAll() {
        List<ProcedimentoRealizado> listaProcedimentosRealizados = service.getAll();

        return new ResponseEntity<List<ProcedimentoRealizado>>(listaProcedimentosRealizados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoRealizado> getById(@PathVariable("id") long id) {
        ProcedimentoRealizado procedimentoRealizado = service.findById(id);

        return new ResponseEntity<ProcedimentoRealizado>(procedimentoRealizado, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProcedimentoRealizado> insertProcedimentoRealizado(@RequestBody ProcedimentoRealizado procedimentoRealizado) {
        if (procedimentoRealizado.getId() == 0) {
            Procedimento procedimento = procedimentoService.findById(procedimentoRealizado.getProcedimento().getId());

            if (procedimento == null) {
                return ResponseEntity.badRequest().build();
            }

            procedimentoRealizado.setProcedimento(procedimento);

            service.save(procedimentoRealizado);
            return new ResponseEntity<ProcedimentoRealizado>(procedimentoRealizado, HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedimentoRealizado> update(@PathVariable long id, @RequestBody ProcedimentoRealizado procedimentoRealizado) {
        ProcedimentoRealizado procedimentoRealizadoAntigo = service.findById(id);
        Procedimento procedimentoNovo;

        if (procedimentoRealizadoAntigo == null) {
            return ResponseEntity.notFound().build();
        }
        procedimentoNovo = procedimentoService.findById(procedimentoRealizado.getProcedimento().getId());

        if (procedimentoNovo == null) {
            return ResponseEntity.badRequest().build();
        }

        procedimentoRealizadoAntigo.setDescricao(procedimentoRealizado.getDescricao());
        procedimentoRealizadoAntigo.setProcedimento(procedimentoNovo);
        procedimentoRealizadoAntigo.setValor(procedimentoRealizado.getValor());

        service.save(procedimentoRealizadoAntigo);

        return new ResponseEntity<ProcedimentoRealizado>(procedimentoRealizadoAntigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProcedimentoRealizado> delete(@PathVariable("id") long id) {
        ProcedimentoRealizado procedimentoRealizadoAntigo = service.findById(id);

        if (procedimentoRealizadoAntigo == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return new ResponseEntity<ProcedimentoRealizado>(procedimentoRealizadoAntigo, HttpStatus.OK);
    }

    @GetMapping("/procedimento/{id}")
    public ResponseEntity<List<ProcedimentoRealizado>> getByProcedimento(@PathVariable("id") long procedimentoId) {
        Procedimento procedimento = procedimentoService.findById(procedimentoId);
        List<ProcedimentoRealizado> listaProcedimentosRealizado;

        if (procedimento == null) {
            return ResponseEntity.badRequest().build();
        }
        
        listaProcedimentosRealizado = service.getByProcedimento(procedimento);

        return new ResponseEntity<List<ProcedimentoRealizado>>(listaProcedimentosRealizado, HttpStatus.OK);
    }

}
