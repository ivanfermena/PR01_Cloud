# PR01_Cloud

## Autores
- Pedro A. Carrasco Ponce
- Iván Fernández Mena

## Creación de la AMI
Una vez hemos lanzado el proyecto y comprobado que funciona, hemos creado un fichero .service para systemd y lo hemos copiado a `/etc/systemd/system`, el fichero es el siguiente:

```
[Unit]
Description=PR01 Cloud
After=network.target
StartLimitIntervalSec=0

[Service]
Type=simple
Restart=always
RestartSec=1
User=ubuntu
ExecStart=/usr/bin/env bash /home/ubuntu/runEventsApp.sh

[Install]
WantedBy=multi-user.target
```

Con el fichero ya creado, para ejecutar el servicio sería:
```
$ systemctl start eventsapp
```

y para que se ejecute siempre en el arranque:

```
$ systemctl enable eventsapp
```

Una vez hecho esto, creamos la AMI según se puede ver en el vídeo, de esta forma, esta AMI siempre ejecutará al arranque el servidor de Spring Boot con la aplicación.