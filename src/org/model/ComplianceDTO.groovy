package org.model;

/**
 * Created on 2022/12/15
 *
 * @author linjianshan
 */

class Compliance {
    LicenseDesc repo_license_legal
    LicenseDesc spec_license_legal
    LicenseDesc license_in_scope
    LicenseDesc repo_copyright_legal
}

class LicenseDesc {
    Boolean pass
    String result_code
    String notice
    def detail
}

public class Cat {
    def name
    def age
}







