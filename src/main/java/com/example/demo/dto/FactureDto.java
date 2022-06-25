package com.example.demo.dto;

import com.example.demo.model.Facture;
import com.example.demo.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FactureDto {
    private Long id;
    private String ref;
    private String agence_name;
    private double prix;
    private boolean payed;
    public static Facture toEntity(FactureDto factureDto) {
        if (factureDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            FactureDto.class.getName()
                    )
            );
        }

        Facture facture=Facture.builder()
                .prix(factureDto.getPrix())
                .ref(factureDto.getRef())
                .agence_name(factureDto.getAgence_name())
                .payed(factureDto.isPayed())
                .build();
        facture.setId(factureDto.id);
        return facture;
    }

    public static FactureDto fromEntity(Facture facture) {
        if (facture == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Facture.class.getName()
                    )
            );
        }

        return FactureDto.builder()
                .id(facture.getId())
                .ref(facture.getRef())
                .agence_name(facture.getAgence_name())
                .prix(facture.getPrix())
                .payed(facture.isPayed())
                .build();
    }
}
