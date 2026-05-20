package com.buildmypc.builder_service.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PcBuildRequestDTO {

    @NotBlank(message = "Debes darle un nombre a tu ensamble")
    private String nombreEnsamble;

    @NotNull(message = "Falta elegir un procesador (CPU)")
    private Long cpuId;

    @NotNull(message = "Falta elegir una placa madre (Motherboard)")
    private Long motherboardId;

    @NotNull(message = "Falta elegir la memoria RAM")
    private Long ramId;

    @NotNull(message = "Falta elegir la tarjeta grafica (GPU)")
    private Long gpuId;

    @NotNull(message = "Falta elegir el almacenamiento (Storage)")
    private Long storageId;

    @NotNull(message = "Falta elegir la fuente de poder (PSU)")
    private Long psuId;
}
