require 'fileutils'

# Waxeye bin location
WAXEYE_BIN = File.expand_path("../../tp/waxeye-0.8.0/bin/waxeye")
OUTPUT_JAVA_LOC = File.expand_path("../../src/com/kylek/ripe/waxeye")
PACKAGE_NAME = "package com.kylek.ripe.waxeye;"
TEMP_WAXEYE_NAME = File.expand_path("temp.waxeye")
IMPORT_REGEX = /^#import (.*)$/

# Clear out everything currently in the OUTPUT_JAVA_LOC
FileUtils.rm_rf(OUTPUT_JAVA_LOC) unless !File.exists?(OUTPUT_JAVA_LOC)
FileUtils.mkdir(OUTPUT_JAVA_LOC)

def self.main
  grammars = []
  grammars << "Attributes.waxeye"
  grammars << "IngredientsList.waxeye"

  grammars.each do |cur|
    
    # Load the contents of the current file
    cur_contents = File.open(cur, "r").read
    
    # Do any imports we can
    cur_contents.scan(IMPORT_REGEX).each do |cur_match|
      file_name = cur_match[0]
      
      # Try to open the imported file
      import_contents = File.open(file_name, "r").read
      
      # Append the contents to the end of our temp waxeye
      cur_contents += import_contents
    end
    
    # Open the temp file and write out our cur_contents
    f = File.open(TEMP_WAXEYE_NAME, "w")
    f.write(cur_contents)
    f.flush
    f.close
    
    # Build the current grammar
    puts "---- Building #{cur} ----"
    cmd = "#{WAXEYE_BIN} -g java #{OUTPUT_JAVA_LOC} #{TEMP_WAXEYE_NAME}"
    puts cmd
    system cmd
    
    puts "---- Editing files built for #{cur} ----"
    Dir.chdir(OUTPUT_JAVA_LOC) do
      
      # There are modifications we need to make to Parser.java and Type.java
      
      name = cur.split(".")[0]
      
      # Parser.java
      modify_file("Parser.java", name)
      # Type.java
      modify_file("Type.java", name)
      
    end
    
    puts "------ Done building #{cur} ------"
    
  end
  
  # Remove our temp waxeye file
  FileUtils.rm(TEMP_WAXEYE_NAME)

end

def self.modify_file(filename, waxeye_name)
  puts "--- Editing #{filename} ---"
  java_file_contents = File.open(filename, "r").read
  java_file_contents = "#{PACKAGE_NAME}\n" + java_file_contents
  
  java_file_contents.gsub!("public final class Parser", "public final class #{waxeye_name}Parser")
  java_file_contents.gsub!("public Parser", "public #{waxeye_name}Parser")
  java_file_contents.gsub!("public enum Type", "public enum #{waxeye_name}Type")
  java_file_contents.gsub!("<Type>", "<#{waxeye_name}Type>")
  java_file_contents.gsub!("Type.", "#{waxeye_name}Type.")
  
  puts "--- Outputting file contents to #{waxeye_name}#{filename} ---"
  fileObj = File.open("#{waxeye_name}#{filename}", "w")
  fileObj.write(java_file_contents)
  fileObj.flush
  fileObj.close
  
  # Remove the original java file
  puts "--- Removing #{filename} ---"
  FileUtils.rm(filename)
end
  
main
