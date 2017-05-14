//
//  ListesViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ListesViewController: UIViewController, UITableViewDataSource,UITableViewDelegate {
    
    let reuseIdentifier = "listeCell"
    
    let listes = ["Liste de mathis","Liste de simon","Liste d'informatique"]
    
    let headerTableView = VCHeaderListe()
    
    lazy var listesTableView : UITableView = {
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
        self.navigationItem.title = "Mes listes"
        listesTableView.register(VCListeCell.self, forCellReuseIdentifier: reuseIdentifier)
        headerTableView.buttonAjouter.addTarget(self, action: #selector(handleAjouter), for: .touchUpInside)
        setupViews()
    }
    
    func handleAjouter() {
        let controller = AjouterListeViewController()
        let navController = UINavigationController(rootViewController: controller)
        present(navController, animated: true, completion: nil)
    }

    func setupViews() {
        
        self.view.addSubview(headerTableView)
        
        headerTableView.topAnchor.constraint(equalTo: self.view.topAnchor, constant : 10).isActive = true
        headerTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        headerTableView.heightAnchor.constraint(equalToConstant: 40).isActive = true
        headerTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        
        
        self.view.addSubview(listesTableView)
        
        listesTableView.topAnchor.constraint(equalTo: headerTableView.bottomAnchor, constant : 10).isActive = true
        listesTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        listesTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        listesTableView.heightAnchor.constraint(equalTo: self.view.heightAnchor).isActive = true
        
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.listes.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 45
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:VCListeCell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier)! as! VCListeCell
        cell.setText(text: listes[indexPath.row])
        
        return cell
        
    }
//    func insertRow(timer : Timer) {
//        themeTableView.insertRows(at: [timer.userInfo as! IndexPath], with: .automatic)
//    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let controller = MotsViewController()
        controller.navigationItem.title = listes[indexPath.row]
        self.navigationController?.pushViewController(controller, animated: true)
    }
}
