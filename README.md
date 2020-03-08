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

#### Ajouter de nouveaux obstacles**

Est mis à disposition ne nombreux éléments dans:

> src\fr\rob4\simulation\geometrie

On y retrouve notemment 5 **formes**:

- Segment
- Cercle
- ArcDeCercle
- Rectangle
- Polygone

Toutes ces formes sont de potentielles formes des **tâches** à mettre en tant qu'argumment dans le constructeur de tâche.

### Le robot

Le robot présente plusieurs caractéristiques:

- Il est circulaires, de diamètre 34cm.
- Il est équipé de 3 **capteurs de contact** lui permettant de repérer des collisions avec les éléments collisionnables de son environement.
- Il est équipé d'un **capteur de saleté** en son centre. Il se déclenche s'il détecte une zone de saleté. Ce capteur à un diamètre de 5cm.
- La vitesse maximum de déplacement est de 1m/s.
- Le moteur se déplace en actionant ses roues placés sur sa périphérie.

#### Ajouter des capteurs

Se rendre dans le package:

> src.fr.rob4.simulation.element.module

Créer une nouvelle classe qui *implements* l'interface **IModule**.

**NB:** Si le robot doit impérativement posséder ce nouveau capteur, il est possible de modifier la méthode:

> creeCapteursStd(Point2D centre, double orientation)

dans la calsse **SimulationStdBuilder** située dans:

> src\fr\rob4\simulation

et dans la **ElementFactory** dans ce même dossier.

**NB:** il reste tout à fait possible de créer un robot manuellement et d'ajouter à la main les capteurs 1 à 1 .

### L' <<intelligence>>

Pour se déplacer dans son environement, le robot doit posséder une **stratégie**. Grâce aux informations fournies par les **capteurs** du robot, la stratégie va pouvoir le piloter dans la simulation.

#### Stratégie implémentée

Actuellement, le robot possède une seule stratégie: **StrategieAleatoire**.

Le robot se déplace en ligne droite tant qu'il ne rencontre pas d'obstacle. Toute **tâche** sur son passage sera nettoyée si le **capteur de saleté** passe dessus.

Dès que les **capteurs de contact** repèrent une collision avec un **élement** collisionnable de la simulation, le robot va rentrer dans plusieurs états concécutifs. D'abord, il s'arrête puis recule de 2cm. Une nouvelle orientation est ensuite choise au hasard. Enfin, le robot reprend sa route linéaire jusqu'au prochain obstacle.

#### Comment implémenter une nouvelle stratégie?

Premièrement se placer dans le package:

> src.fr.rob4.simulation.strategie

Créer ensuite une nouvelle classe qui *implements* l'interface **IStratégie**. Classe à remplir avec toutes les méthodes nécessaires à la strategie souhaitée. 

**IMPORTANT:** la stratégie n'est pas sensé avoir accès à la **simulation**. Les **capteurs** font le pont entre les deux.

### La simulation

La simulation s' update toutes les 0,01s.

A chaque itération, la simulation s'actualise. Puis elle demande à tous les éléments **actualisables** (inclus le robot, la startégie et les capteurs) de s'actualiser aussi. Elle demande les collisions entre tous les éléments **collisionnables**, si il y a bien une collision entre 2 de ces éléments, la simulation demande à chacun de gérer la collision. Ensuite, on s'intéresse au robot, si le **capteur de saleté** à repérer une zone de saleté, cette dernière est supprimée de la simulation.

Cette boucle se réitère jusqu'à ce qu'il n'y ai plus d'éléments **INettoyable** dans la simulation.

### UML

Vu la compléxité de l'UML, il a été jugé non pertinant de mentionner les getters retournant simplement un attribut de la classe. Cependant les getters présentant des calculs sont conservés.

## Liste des objectifs réalisés

- Les tâches, les obstacles, les capteurs et le robot peuvent accepter n'importe quelle formes
- Une fenêtre pour visualiser la simulation
- Un affichage des éléments
- Une stratégie aléatoire (avec machine à état embarquée)
- La possibilité d'implémenter de nouvelle startégies
- La possibilité d'implémenter de nouveaux capteur
- Abstraction des types via un **pattern abstract factory**
- Gestion des collisions avec calcul du point d'intersection.
- Mise à jour de l'affichage des capteurs lorsqu'ils s'activent  (changment de couleur)
- Dissociation du modèle et de l'affichage.
- **pattern builder** pour simplifier la construction de la simulation