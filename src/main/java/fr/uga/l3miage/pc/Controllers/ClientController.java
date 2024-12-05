package fr.uga.l3miage.pc.Controllers;


import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    public List<ClientEntity> getAllClients() {
        return clientService.getAllClients();
    }


    public ResponseEntity<ClientEntity> getClientById(@PathVariable Long id) {
        ClientEntity client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }


    public ClientEntity createClient(@RequestBody ClientEntity clientEntity) {
        return clientService.createClient(clientEntity);
    }


    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}