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
extension UIColor {
    convenience init (hex : NSString)  {
        let char0 = hex.character(at : 0)
        let char1 = hex.character(at: 1)
        
        let RED = String(char0 + char1)
        let redValue = UInt8(RED, radix:16)
        
        let char2 = hex.character(at : 2)
        let char3 = hex.character(at: 3)
        
        let GREEN = String(char2 + char3)
        let greenValue = UInt8(GREEN, radix:16)
        
        let char4 = hex.character(at : 4)
        let char5 = hex.character(at: 5)
        
        let BLUE = String(char4 + char5)
        let blueValue = UInt8(BLUE, radix:16)
        
        self.init(r: CGFloat(redValue!), g: CGFloat(greenValue!), b: CGFloat(blueValue!))
    }
}
