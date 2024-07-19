package server.api;

import commons.Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ProviderRepository;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {
    private final ProviderRepository providerRepository;

    /**
     * Constructor for the ProviderController.
     *
     * @param providerRepository the repository for providers
     */
    public ProviderController(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Get an invoice by its id.
     *
     * @param id the id of the invoice
     * @return the invoice with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable("id") long id) {
        if (!providerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(providerRepository.findById(id).get());
    }

    /**
     * Get all providers.
     *
     * @return all providers
     */
    @GetMapping(path = {"", "/"})
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    /**
     * Edit a provider.
     *
     * @param id the id of the provider
     * @return the new provider
     */
    @PutMapping("/{id}")
    public ResponseEntity<Provider> editProvider(@PathVariable long id,
                                                 @RequestBody Provider updatedProvider) {
        if (!providerRepository.existsById(id) || isNullOrEmptyProvider(updatedProvider)) {
            return ResponseEntity.badRequest().build();
        }
        Provider saved = providerRepository.save(updatedProvider);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete a provider by its id.
     *
     * @param id the id of the provider to delete
     * @return the deleted provider
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Provider> deleteProvider(@PathVariable long id) {
        if (!providerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Provider provider = providerRepository.findById(id).get();
        providerRepository.deleteById(id);
        return ResponseEntity.ok(provider);
    }

    /**
     * Add a provider.
     *
     * @param provider the provider to add
     * @return the added provider
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Provider> addProvider(@RequestBody Provider provider) {
        if (isNullOrEmptyProvider(provider)) {
            return ResponseEntity.badRequest().build();
        }
        Provider saved = providerRepository.save(provider);
        return ResponseEntity.ok(saved);
    }

    /**
     * Check if a provider is null or has null fields.
     *
     * @param provider the provider to check
     * @return true if the provider is null or has null fields, false otherwise
     **/
    private static boolean isNullOrEmptyProvider(Provider provider) {
        //TODO: Revise this method when you know exactly what fields are optional
        return provider == null || provider.getName() == null || provider.getAccount() == null
            || provider.getAddress() == null || provider.getBank() == null || provider.getCif() == null
            || provider.getName().isBlank() || provider.getAccount().isBlank()
            || provider.getAddress().isBlank() || provider.getBank().isBlank() || provider.getCif().isBlank();
    }
}
