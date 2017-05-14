//
//  MotsViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class MotsViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    var mots : [String] = ["Computer - Ordinateur","Laptop - Ordinateur portable","View - Vue"]
    let reuseIdentifier = "motCell"
    
    lazy var motsTableView : UITableView = {
        var tv = UITableView()
        tv.delegate = self
        tv.dataSource = self
        tv.backgroundColor = .clear
        tv.separatorStyle = .none
        tv.translatesAutoresizingMaskIntoConstraints = false
        return tv
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleRevenir)), animated: true)
        self.navigationItem.setRightBarButton(UIBarButtonItem(title: "Ajouter", style: .plain, target: self, action: #selector(handleAjouter)), animated: true)
        self.motsTableView.register(VCMotCell.self, forCellReuseIdentifier: reuseIdentifier)
        self.view.backgroundColor = .white
        setupViews()
    }
    
    func handleAjouter() {
        
    }
    
    func setupViews(){
        self.view.addSubview(motsTableView)
        
        motsTableView.topAnchor.constraint(equalTo: view.topAnchor, constant : 10).isActive = true
        motsTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        motsTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        motsTableView.heightAnchor.constraint(equalTo: self.view.heightAnchor).isActive = true
    }
    
    func handleRevenir() {
        self.navigationController?.popViewController(animated: true)
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.mots.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 45
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:VCMotCell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier)! as! VCMotCell
        cell.setText(text: mots[indexPath.row])
        
        return cell
        
    }
}
