package com.centrala.naucna_centrala.DTO;

import java.util.ArrayList;
import java.util.List;

public class TransakcijeListDTO {

    private List<TransakcijeDTO> list_transakcije;

    public TransakcijeListDTO(List<TransakcijeDTO> list_transakcije) {
        this.list_transakcije = list_transakcije;
    }

    public TransakcijeListDTO() {
        this.list_transakcije = new ArrayList<>();
    }

    public List<TransakcijeDTO> getList_transakcije() {
        return list_transakcije;
    }

    public void setList_transakcije(List<TransakcijeDTO> list_transakcije) {
        this.list_transakcije = list_transakcije;
    }
}
