//
//  ListeCell.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class VCListeCell: UITableViewCell {
    
    var labelListe : UILabel = {
        let label = UILabel()
        label.text = "Overflow"
        label.font = UIFont(name: "Helvetica-Light", size: 15)
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textAlignment = .left
        return label
    }()

    let separatorLine : UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = UIColor(rgb: 0x95989A)
        view.alpha = 0.5
        return view
    }()
    
    let imageMore : UIImageView = {
        let image = UIImageView()
        image.image = #imageLiteral(resourceName: "More")
        image.translatesAutoresizingMaskIntoConstraints = false
        return image
    }()
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        self.selectionStyle = .none
        self.backgroundColor = UIColor.white
        setupViews()
        
    }
    
    func setText(text : String) {
        labelListe.text = text
    }
    
    func setupViews() {
        self.addSubview(imageMore)
        
        imageMore.centerYAnchor.constraint(equalTo: self.centerYAnchor).isActive = true
        imageMore.rightAnchor.constraint(equalTo: self.rightAnchor, constant: -15).isActive = true
        imageMore.widthAnchor.constraint(equalToConstant: 15).isActive = true
        imageMore.heightAnchor.constraint(equalToConstant: 15).isActive = true
        
        self.addSubview(labelListe)
        
        labelListe.centerYAnchor.constraint(equalTo: self.centerYAnchor).isActive = true
        labelListe.leftAnchor.constraint(equalTo: self.leftAnchor).isActive = true
        labelListe.heightAnchor.constraint(equalTo: self.heightAnchor).isActive = true
        labelListe.widthAnchor.constraint(equalTo: self.widthAnchor).isActive = true
        
        self.addSubview(separatorLine)
        
        separatorLine.centerXAnchor.constraint(equalTo: self.centerXAnchor).isActive = true
        separatorLine.bottomAnchor.constraint(equalTo: self.bottomAnchor).isActive = true
        separatorLine.heightAnchor.constraint(equalToConstant : 1).isActive = true
        separatorLine.widthAnchor.constraint(equalTo: self.widthAnchor).isActive = true
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
