Version-maven-plugin
====================

Introduction
------------
Ce projet est né du besoin de pouvoir gérer les versions des projets Maven automatiquement. 
De façon, plus précise, ce plugin permet :

* d'incrémenter les numéros de version selon un algorithme qui peut être spécifié dans sa configuration.
* de passer la version du statut SNAPSHOT à RELEASE et réciproquement

Versions
--------

Du fait de la dépendance du plugin avec la version de Maven utilisé, voici la correspondance entre les versions 
majeures du plugin et celle de maven :

<table>
    <tr><td>Version de Maven</td><td>Version du plugin</td></tr>
    <tr><td>3.2.5</td><td>1.0.x</td></tr>
    <tr><td>3.3.3</td><td>1.1.x</td></tr>
</table>

Installation
------------
Pour l'installation de ce plugin, il suffit de suivre les étapes suivantes : 

* télécharger les sources du projet : git clone git@github.com:fmoule/version-maven-plugin.git
* compiler le projet : mvn clean install

Commandes
---------
- help : Affiche toutes les commandes disponibles
- release-version : Modifie la version en utilisant l'algorithme par défaut
- add-snapshot : Commande permettant d'incrémenter le numéro de version en ajoutant le suffixe "snapshot"
- remove-snapshot : Commande permettant de supprimer le suffixe "snapshot"