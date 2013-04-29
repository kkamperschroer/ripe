
# Waxeye bin location
WAXEYE_BIN = "/media/Warehouse/Dropbox/School/Eclipse/workspace/ripe/tp/waxeye-0.8.0/bin/waxeye"

OUTPUT_JAVA_LOC = "/media/Warehouse/Dropbox/School/Eclipse/workspace/ripe/src/com/kylek/ripe/waxeye"

GRAMMAR_LOC = "/media/Warehouse/Dropbox/School/Eclipse/workspace/ripe/src/waxeye/recipe.waxeye"

PACKAGE_NAME = "package com.kylek.ripe.waxeye;"

cmd = "#{WAXEYE_BIN} -g java #{OUTPUT_JAVA_LOC} #{GRAMMAR_LOC}"

puts cmd
system cmd

# Now add the package statement to the generated files
Dir.chdir(OUTPUT_JAVA_LOC) do
  java_files = Dir.glob("*.java")
  java_files.each do |cur_file|
    fileObj = File.open(cur_file, "r")
    lines = fileObj.readlines
    fileObj.close

    lines = ["#{PACKAGE_NAME}\n"] + lines
    fileObj = File.open(cur_file, "w")
    lines.each do |cur_line|
      fileObj.write(cur_line)
    end
    fileObj.close
  end

end


=begin
waxeye [ <option> ... ] <grammar>
 where <option> is one of
 Waxeye modes:
/ -g <language> <dir> : Generate
| -i : Interpret
\ -t <test> : Test
 Grammar options:
  -m : Modular Grammar - default: false
  -s <start> : Starting non-terminal - default: first non-terminal
 Parser options:
  -c <comment> : Header comment for generated files - default: none
  -e <eof> : Check parser consumes all input - default: true
  -n <namespace> : Module or package namespace - default: none
  -p <prefix> : Name prefix for generated files - default: none
 Misc options:
  --debug : Activates debug information
  --version : Prints version number and copyright notice
  --help, -h : Show this help
  -- : Do not treat any remaining argument as a switch (at this level)
 /|\ Brackets indicate mutually exclusive options.
 Multiple single-letter switches can be combined after one `-'; for
  example: `-h-' is the same as `-h --'
=end
