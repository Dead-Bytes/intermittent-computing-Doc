// Commands to run the code
//  clang++ -shared -o findreturn.so findreturn.cpp `llvm-config --cxxflags --ldflags --libs` -fPIC
//  opt -S -load ./findreturn.so -findreturn -enable-new-pm=0 < intopre.ll >intopre_trigger.ll

// This pass finds the return statement of the program and tagg it with a function.
#include "llvm/IR/Function.h"
#include "llvm/IR/Instructions.h"
#include "llvm/IR/Module.h"
#include "llvm/Pass.h"
#include "llvm/IR/IRBuilder.h"
#include "llvm/Support/raw_ostream.h"

using namespace llvm;

namespace {
    struct FindReturn : public ModulePass {
        static char ID; 
        FindReturn() : ModulePass(ID) {}

        bool runOnModule(Module &M) override {
            bool FoundReturn = false;
            bool isactivation = false;
            bool isthreshold = false;
            for (Function &F : M) {
                for (BasicBlock &BB : F) {
                    for (Instruction &I : BB){
                      //if (auto *callInst = dyn_cast<CallInst>(&I)) {
                            //Function *calledFunc = callInst->getCalledFunction();
                            //if (calledFunc->getName() == "activation") {
                                //isactivation = true;}}

                          //if (auto *callInst = dyn_cast<CallInst>(&I)) {
                            //Function *calledFunc = callInst->getCalledFunction();
                            //if (calledFunc->getName() == "nvmwrite") {
                              //  isthreshold = true;}}


                      //for (auto I = BB.begin(), E = BB.end(); I != E; ++I) { 
                        //if(isactivation == true)  {                   
                        if (ReturnInst *Ret = dyn_cast<ReturnInst>(&I)) {
                            FoundReturn = true;
                            errs() << "Found return statement in function:: " << F.getName() << "\n";
                            errs() << "Return instruction:: " << *Ret << "\n";
                          LLVMContext &Context = BB.getContext();
                          FunctionType *FTy = FunctionType::get(Type::getVoidTy(Context), false);
                          FunctionCallee Functionreturn = BB.getModule()->getOrInsertFunction("check_energy", FTy);

                          CallInst::Create(Functionreturn, "", &BB.back());
                          
                            //}

                        }
                   //else
                       //{
                         //if( isthreshold == true)
                         //{
                           //break; }
                   }//}
                    //if( isthreshold == true)
                         //{
                          // break; }
                    }
                   
                    //if( isthreshold == true)
                         //{
                           //break; }
                    }
                  //if( isthreshold == true)
                         //{
                          // break; }
               //}
            //}
            return FoundReturn;
        }
    };
}

char FindReturn::ID = 0;

static RegisterPass<FindReturn> X("findreturn", "Find return statements in module");

