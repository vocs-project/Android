//
//  labelMenu.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class LabelMenu: UILabel {

    override init(frame: CGRect) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.text = "labelMenu"
        self.font = UIFont(name: "HelveticaNeue", size: 15)
        self.textColor = .gray
    }

}
