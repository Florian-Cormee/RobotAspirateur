# README

## Projet POO : simulateur de robot mobile

Le **Projet POO** est un projet de programmation en **Java**. Il a été réalisé dans le contexte du module d'étude de **Programmation Orienté Objet** (POO) de l'année 4 du cursus robotique de Polytech Sorbonne.
Il a pour objectif de mettre en pratique nos cours sur la programmation object en **Java**. Ceci en réalisant la simulation d'un robot aspirateur dans son environement.

### Auteurs
- Florentin BEROUJON
- Florain CORMEE

## Lancer la simulation

Pour lancer la **simulation**, il faut se rendre dans la classe **Main** du projet Eclipse.
> \src\fr\rob4\simulation\Main.java
Lancer le programme :D

## Présentation du projet

### La pièce à nettoyer

L'environement du robot présente plusieurss caractéristiques:
- Dimension de 4m x 4m.
- Tous les **obstacles** sont circulaires (de taille variée) sauf les murs de la simulation.
- La pièce comporte des **tâches** circulaires, de taille inférieure à celle du robot aspirateur. Ces tâches représentent des zones de saleté.

### Le robot

Le robot présente plusieurs caractéristiques:
- Il est circulaires, de diamètre 34cm.
- Il est équipé de 3 **capteurs de contact** lui permettant de repérer des collisions avec les éléments collisionnables de son environement.
- Il est équipé d'un **capteur de saleté** en son centre. Il se déclenche s'il détecte une zone de saleté. Ce capteur à un diamètre de 5cm.
- La vitesse maximum de déplacement est de 1m/s.
- Le moteur se déplace en actionant ses roues placés sur sa périphérie.

### L' <<intelligence>>

Pour se déplacer dans son environement, le robot doit posséder une **stratégie**. Grâce aux informations fournies par les **capteurs** du robot, la stratégie va pouvoir le piloter dans la simulation.

#### Stratégie implémentée

Actuellement, le robot possède une seule stratégie: **StrategieAleatoire**.

Le robot se déplace en ligne droite tant qu'il ne rencontre pas d'obstacle. Toute **tâche** sur son passage sera nettoyée si le **capteur de saleté** passe dessus.

Dès que les **capteurs de contact** repèrent une collision avec un **élement** collisionnable de la simulation, le robot va rentrer dans plusieurs états concécutifs. D'abord, il s'arrête puis recule de 2cm. Une nouvelle orientation est ensuite choise au hasard. Enfin, le robot reprend sa route linéaire jusqu'au prochain obstacle.

#### Comment implémenter une nouvelle stratégie?

