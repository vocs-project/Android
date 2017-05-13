//
//  VCLabelMenu.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class VCLabelMenu: UILabel {

    init(_ text : String) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.font = UIFont(name: "Helvetica Neue", size: 25)
        self.text = text
        self.textAlignment = .center
        self.textColor = .gray
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}

class VCLabelCopyright : UILabel {
    
    init(view: UIView) {
        super.init(frame: CGRect(x: view.frame.width / 2, y: view.frame.height - 40, width: view.frame.width / 2 , height: 30))
        self.font = UIFont(name:  "Helvetica Neue", size: 15)
        self.text = "@Copyright Vocs 2017"
        self.textAlignment = .center
        self.textColor = .black
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
