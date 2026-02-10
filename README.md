# Exercice de Compilation

## Description

Ecriture d'un générateur en assembleur Beta, d'un arbre abstrait d'affectation

NB: on considère à ce stade que les variables sont à la fois des entiers et des variables globales

Ce projet contient est la correction d'un exercie de TD de Compilation
en Licence MIASHS parcours MIAGE et TAL à l'Université de Lorraine.

## Capture d'écran
```
% ./run
Arbre source :
AFF
└─IDF/x
└─PLUS
  └─MUL
    └─IDF/a
    └─CONST/2
  └─DIV
    └─MOINS
      └─IDF/a
      └─CONST/5
    └─CONST/3


Code généré :
        CMOVE(R0, a)
        PUSH(R0)
        CMOVE(R0, 2)
        PUSH(R0)
        POP(R2)
        POP(R1)
        MUL(R1, R2, R3)
        PUSH(R3)
        CMOVE(R0, a)
        PUSH(R0)
        CMOVE(R0, 5)
        PUSH(R0)
        POP(R2)
        POP(R1)
        SUB(R1, R2, R3)
        PUSH(R3)
        CMOVE(R0, 3)
        PUSH(R0)
        POP(R2)
        POP(R1)
        DIV(R1, R2, R3)
        PUSH(R3)
        POP(R2)
        POP(R1)
        ADD(R1, R2, R3)
        PUSH(R3)
        POP(R0)
        ST(R0, x)

```
## Explications

Ce projet a été développé à partir du package JAVA de gestion
d'un arbre abstrait disponible sur 
le [gitlab de l'Université de Lorraine](https://gitlab.univ-lorraine.fr/roussana5/arbre).

Pour le réaliser, nous avons ajouté un nouveau package `fr.ul.miage.compil.traduction`
qui contient deux classes :

1. la classe Génerateur (voir le td correspondant)
2. et la classe Exemple 1, qui effectue le test sur une affection d'un expression complexe.

Pour adapter le projet *arbre* à notre projet, il a fallu adapter le fichier *pom.xml*
utilisé par Maven et respectant le cycle de vie présenté en TD.

L'adaptation a consisté à modifier les éléments suivants :
- les property *project.bin.classname* et *project.bin.appname* pour indiquer respectivement la classe principale
    et le nom de l'application
- le plugin *appassembler-maven-plugin* pour générer un script d'exécution de l'application
  (ici, il n'y a plus qu'un seul script)


## Prérequis

Utiliser un IDE qui intègre Maven (par exemple Eclipse)

## Installation

1. Télécharger la distribution source du projet (fichier .zip)
2. Décompresser dans un dossier
3. Importer le projet dans votre IDE (pour Eclipse : `File>Import...>Existing Maven Project...`
4. Installer dans le repository local (pour Eclipse: `Run As...>Maven Install`

## Auteur

Azim Roussanaly (IDMC/Université de Lorraine)

## Licence

Licence MIT