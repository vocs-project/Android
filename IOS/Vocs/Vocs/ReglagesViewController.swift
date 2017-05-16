//
//  ReglagesViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ReglagesViewController: UIViewController {

    let labelSiteWeb = VCLabelMenu(text: "Site web : www.exemple.com",size : 20)
    
    var labelCopyright = VCLabelCopyright()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        self.view.backgroundColor = .white
        self.navigationItem.title = "Réglages"
        self.navigationItem.setRightBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(retour)), animated: true)
        setupViews()
    }
    
    func retour() {
        dismiss(animated: true, completion: nil)
    }
    
    func setupViews() {
        self.view.addSubview(labelSiteWeb)
        labelSiteWeb.topAnchor.constraint(equalTo: view.topAnchor, constant: 40).isActive = true
        labelSiteWeb.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelSiteWeb.heightAnchor.constraint(equalToConstant: 50).isActive = true
        labelSiteWeb.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
        self.view.addSubview(labelCopyright)
        labelCopyright.bottomAnchor.constraint(equalTo: self.view.bottomAnchor, constant: -15).isActive = true
        labelCopyright.widthAnchor.constraint(equalTo: view.widthAnchor).isActive = true
        labelCopyright.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        labelCopyright.heightAnchor.constraint(equalToConstant: 30).isActive = true
    }

}
