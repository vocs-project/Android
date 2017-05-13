//
//  ReglagesViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ReglagesViewController: UIViewController {

    let labelSiteWeb = VCLabelMenu("Site web : www.exemple.com")
    
    var labelCopyright : VCLabelCopyright?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Réglages"
        setupViews()
    }
    
    func setupViews() {
        self.view.addSubview(labelSiteWeb)
        labelSiteWeb.topAnchor.constraint(equalTo: view.topAnchor, constant: 40).isActive = true
        labelSiteWeb.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelSiteWeb.heightAnchor.constraint(equalToConstant: 50).isActive = true
        labelSiteWeb.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
        labelCopyright = VCLabelCopyright(view: self.view)
        self.view.addSubview(labelCopyright!)
    }

}
