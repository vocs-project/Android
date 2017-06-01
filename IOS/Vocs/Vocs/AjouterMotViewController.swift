//
//  AjouterMotViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class AjouterMotViewController: UIViewController {

    var delegateMot : AjouterUnMotDelegate!
    
    let labelAjouter = VCTitreLabel(text : "Ajouter un mot")
    let textFieldMot = VCTextFieldLigneBas(placeholder: "Anglais",alignement : .left)
    let textFieldTraductionMot = VCTextFieldLigneBas(placeholder: "Français",alignement : .left)
    var liste : List?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleRevenir)), animated: true)
        self.navigationItem.setRightBarButton(UIBarButtonItem(title: "Ajouter", style: .plain, target: self, action: #selector(handleAjouter)), animated: true)
        self.view.backgroundColor = .white
        setupViews()
    }
    
    func handleAjouter() {
        if let mot = textFieldMot.text, let traduction = textFieldTraductionMot.text {
            if (!(mot.isEmpty || traduction.isEmpty)){
                delegateMot.envoyerMot(mot : Mot(french: mot, english: traduction))
            }
        }
        dismiss(animated: true, completion: nil)
    }
    
    func setupViews(){
        self.view.addSubview(labelAjouter)
        labelAjouter.topAnchor.constraint(equalTo: self.view.topAnchor, constant : 30).isActive = true
        labelAjouter.heightAnchor.constraint(equalToConstant: 30).isActive = true
        labelAjouter.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelAjouter.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        
        
        self.view.addSubview(textFieldMot)
        textFieldMot.topAnchor.constraint(equalTo: labelAjouter.bottomAnchor,constant : 40).isActive = true
        textFieldMot.heightAnchor.constraint(equalToConstant: 30).isActive = true
        textFieldMot.widthAnchor.constraint(equalTo: self.view.widthAnchor,multiplier : 9 / 10).isActive = true
        textFieldMot.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        
        self.view.addSubview(textFieldTraductionMot)
        textFieldTraductionMot.topAnchor.constraint(equalTo: textFieldMot.bottomAnchor,constant : 40).isActive = true
        textFieldTraductionMot.heightAnchor.constraint(equalToConstant: 30).isActive = true
        textFieldTraductionMot.widthAnchor.constraint(equalTo: self.view.widthAnchor,multiplier : 9 / 10).isActive = true
        textFieldTraductionMot.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        
    }
    
    func handleRevenir() {
        dismiss(animated: true, completion: nil)
    }

}
