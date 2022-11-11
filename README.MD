# Elastic Search Demo configuration
## Docker configuration

##	add Docker’s official GPG key
```sh
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```


##	set up the repository
```sh
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

##	check if the source is in
```sh
ls /etc/apt/sources.list.d
```

##	update and install
```sh
sudo apt update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

##	change tables to legacy
```sh
sudo update-alternatives --config iptables   - Legacy
```

##	check status check
```sh
sudo service docker status
sudo service docker start
```

##	assign user to docker
```sh
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker
```

## install docker compose
```sh
sudo curl -L "https://github.com/docker/compose/releases/download/v2.12.2/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

##	get Ip address
```sh
ip addr show eth0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}'
```

##	start server on ip
```sh
sudo service docker stop
sudo dockerd -H 172.27.152.211
```

## Docker compose file
```sh
version: "3.0"
services:
  elasticsearch:
    container_name: es-container
    image: docker.elastic.co/elasticsearch/elasticsearch:7.16.3
    environment:
      - xpack.security.enabled=false
      - "discovery.type=single-node"
    networks:
      - es-net
    ports:
      - 9200:9200
  kibana:
    container_name: kb-container
    image: docker.elastic.co/kibana/kibana:7.16.3
    environment:
      - ELASTICSEARCH_HOSTS=http://es-container:9200
    networks:
      - es-net
    depends_on:
      - elasticsearch
    ports:
      - 5601:5601
networks:
  es-net:
    driver: bridge
```
