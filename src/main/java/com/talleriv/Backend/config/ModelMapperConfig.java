package com.talleriv.Backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Esta clase configura un bean de ModelMapper que se utiliza para mapear objetos entre DTOs y entidades.
// Esto es especialmente útil para reducir el código manual al trabajar con conversiones entre clases.
@Configuration // Indica que esta clase contiene un bean de configuración de Spring.
public class ModelMapperConfig {

    /**
     * Configura y expone un bean de ModelMapper.
     *
     * @return Una instancia configurada de ModelMapper.
     */
    @Bean // Define un bean para que Spring lo gestione automáticamente en el contenedor de IoC.
    public ModelMapper modelMapper() {
        // Crea una nueva instancia de ModelMapper.
        ModelMapper modelMapper = new ModelMapper();
        
        // Configura el comportamiento del mapeador.
        modelMapper.getConfiguration()
                // Permite que ModelMapper haga coincidir nombres de campos entre objetos.
                .setFieldMatchingEnabled(true)
                // Establece el nivel de acceso para manipular campos privados.
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // Configura la estrategia de emparejamiento para usar una coincidencia estándar.
                // Esto define reglas para cómo los campos de origen coinciden con los de destino.
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        
        // Retorna la instancia configurada para su uso en cualquier parte de la aplicación.
        return modelMapper;
    }
}