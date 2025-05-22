    package com.example.SoporteTecnico.controller;

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
    import com.example.SoporteTecnico.model.TipoSoporte;
    import com.example.SoporteTecnico.service.SoporteTecnicoService;
    import jakarta.persistence.EntityNotFoundException;

    @RestController
    @RequestMapping("/api/v1/tiposSoporte")
    public class TipoSoporteController {

        @Autowired
        private SoporteTecnicoService sopService;

        @PostMapping
        public ResponseEntity<?> crearTipoSoporte(@RequestBody TipoSoporte nuevoTipo) {
            try {
                TipoSoporte ts = sopService.createTipoSoporte(nuevoTipo);
                return ResponseEntity.status(HttpStatus.CREATED).body(ts);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear TipoSoporte: " + e.getMessage());
            }
        }
        
        @PutMapping("/{id}")
        public ResponseEntity<?> editarTipoSoporte(@PathVariable Integer id, @RequestBody TipoSoporte tipoSoporte) {
            try {
                TipoSoporte actualizado = sopService.updateTipoSoporte(id, tipoSoporte);
                return ResponseEntity.ok(actualizado);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar TipoSoporte: " + e.getMessage());
            }
        }
        
        @GetMapping
        public ResponseEntity<List<TipoSoporte>> listarTiposSoporte() {
            List<TipoSoporte> lista = sopService.getTipoSoportes();
            if (lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        }
        
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarTipoSoporte(@PathVariable Integer id) {
            boolean deleted = sopService.deleteTipoSoporteById(id);
            if (deleted) {
                return ResponseEntity.ok("TipoSoporte eliminado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TipoSoporte no encontrado.");
            }
        }
    }
