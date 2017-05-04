//
//  Extensions.swift
//  Vocs
//
//  Created by Mathis Delaunay on 04/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import Foundation
import UIKit

extension UIColor {
    convenience init(r : CGFloat, g : CGFloat,b :CGFloat){
        self.init(red: r / 255, green:  g / 255, blue: b / 255, alpha: 1)
    }
}
