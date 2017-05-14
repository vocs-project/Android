//
//  BoutonTraduction.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class VCButtonExercice: UIButton {

    init(_ title : String, color : UIColor) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.setTitle(title, for: .normal)
        self.backgroundColor = color
        self.layer.cornerRadius = 30
        self.setAttributedTitle(NSAttributedString(string: title, attributes: [NSFontAttributeName : UIFont(name: "HelveticaNeue-Medium",size : 20 ) as Any, NSForegroundColorAttributeName : UIColor.white as Any]), for: .normal)
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOffset = CGSize(width: -1, height: 1)
        self.layer.shadowRadius = 1
        self.layer.shadowOpacity = 0.5
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}

class VCButtonValidate : UIButton {
    
    let validateImage : UIImageView = {
        let image = UIImageView()
        image.image = #imageLiteral(resourceName: "Check")
        image.translatesAutoresizingMaskIntoConstraints = false
        return image
    }()
    
    init () {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.backgroundColor = UIColor(rgb: 0x1E7FBD)
        self.layer.shadowOffset = CGSize(width: -1, height: 1)
        self.layer.shadowRadius = 1
        self.layer.shadowOpacity = 0.5
        self.layer.cornerRadius = 10
        setupViews()
    }
    
    func setupViews() {
        self.addSubview(validateImage)
        validateImage.centerYAnchor.constraint(equalTo: self.centerYAnchor).isActive = true
        validateImage.centerXAnchor.constraint(equalTo: self.centerXAnchor).isActive = true
        validateImage.heightAnchor.constraint(equalTo: self.heightAnchor, multiplier: 3/4).isActive = true
        validateImage.widthAnchor.constraint(equalTo: self.widthAnchor, multiplier: 3/4).isActive = true
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
