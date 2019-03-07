cmake_minimum_required(VERSION 2.8)
project(Sockets C++)


add_compile_options(-std=c++11)
add_executable(main main.c++)
target_link_libraries(main wsock32)