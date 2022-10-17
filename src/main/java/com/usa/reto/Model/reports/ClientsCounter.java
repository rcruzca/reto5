package com.usa.reto.Model.reports;

import com.usa.reto.Model.Client;

public class ClientsCounter {
    private Long total;
    private Client client;

    public ClientsCounter(Long total, Client client) {
        this.total = total;
        this.client = client;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
