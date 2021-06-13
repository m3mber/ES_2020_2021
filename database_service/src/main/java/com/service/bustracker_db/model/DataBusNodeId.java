package com.service.bustracker_db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataBusNodeId
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nodeId;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String node_id)
    {
        this.nodeId = node_id;
    }

    @Override
    public String toString()
    {
        return "DataBusInfo{" +
                "id=" + id +
                ", nodeId='" + nodeId + '\'' +
                '}';
    }
}

