require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))
folly_version = '2022.05.16.00'
folly_compiler_flags = '-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -Wno-comma -Wno-shorten-64-to-32'

Pod::Spec.new do |s|
  s.name         = "react-native-e2ee"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "14.0" }
  s.source       = { :git => "https://github.com/siepra/react-native-e2ee.git", :tag => "#{s.version}" }

  s.swift_version = '5.8'
  s.static_framework = true

  s.source_files = "ios/**/*.{h,m,mm,swift}"

  s.compiler_flags = folly_compiler_flags

  s.pod_target_xcconfig = {
    "DEFINES_MODULE" => "YES",
    "SWIFT_COMPILATION_MODE" => "wholemodule",
    "CLANG_CXX_LANGUAGE_STANDARD" => "c++17",
    # "HEADER_SEARCH_PATHS" => "\"$(PODS_TARGET_SRCROOT)/cpp/\"/** ", # This will link the headers at compile time, flag passed directly to the compiler
  }

  s.dependency "SwiftyRSA"

  s.dependency "React"
  s.dependency "React-Core"
  s.dependency "React-Codegen"
  s.dependency "RCT-Folly", folly_version
  s.dependency "RCTRequired"
  s.dependency "RCTTypeSafety"
  s.dependency "ReactCommon/turbomodule/core"
end
