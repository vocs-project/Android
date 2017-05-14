//
//  AjouterListeViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class AjouterListeViewController: UIViewController {

    var delegateAjouter: AjouterUneListeDelegate!
    let textFieldNomListe = VCTextFieldLigneBas(placeholder: "Nom de la liste",alignement : .left)
    let labelAjouter = VCTitreLabel(text : "Ajouter une liste")
    let validateButton = VCButtonValidate()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .white
        self.hideKeyboardWhenTappedAround()
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleRevenir)), animated: true)
        self.navigationItem.setRightBarButton(UIBarButtonItem(title: "Ajouter", style: .plain, target: self, action: #selector(handleAjouter)), animated: true)
        validateButton.addTarget(self, action: #selector(handleAjouter), for: .touchUpInside)
        setupViews()
        self.navigationItem.title = "Mes listes"
    }

    func handleRevenir() {
        dismiss(animated: true, completion: nil)
    }
    
    func handleAjouter() {
        if let liste = textFieldNomListe.text {
            if !(liste.isEmpty){
                delegateAjouter.envoyerListe(texte : liste)
            }
        }
        dismiss(animated: true, completion: nil)
    }
    
    func setupViews() {
        self.view.addSubview(textFieldNomListe)
        textFieldNomListe.centerYAnchor.constraint(equalTo: self.view.centerYAnchor,constant : -40).isActive = true
        textFieldNomListe.heightAnchor.constraint(equalToConstant: 30).isActive = true
        textFieldNomListe.widthAnchor.constraint(equalToConstant: 250).isActive = true
        textFieldNomListe.centerXAnchor.constraint(equalTo: self.view.centerXAnchor,constant : -20).isActive = true
        
        self.view.addSubview(labelAjouter)
        labelAjouter.topAnchor.constraint(equalTo: self.view.topAnchor, constant : 30).isActive = true
        labelAjouter.heightAnchor.constraint(equalToConstant: 30).isActive = true
        labelAjouter.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelAjouter.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        
        self.view.addSubview(validateButton)
        validateButton.leftAnchor.constraint(equalTo: textFieldNomListe.rightAnchor,constant : 10).isActive = true
        validateButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        validateButton.widthAnchor.constraint(equalToConstant: 40).isActive = true
        validateButton.centerYAnchor.constraint(equalTo: self.view.centerYAnchor,constant : -40).isActive = true
    }

}
