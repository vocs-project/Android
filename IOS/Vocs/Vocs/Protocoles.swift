//
//  Protocoles.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import Foundation

protocol AjouterUnMotDelegate {
    func envoyerMot(mot : Mot)
}

protocol AjouterUneListeDelegate {
    func envoyerListe(texte : String)
}
