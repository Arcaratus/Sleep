package arc.sleep.asm.patches;

import arc.sleep.asm.MethodToPatch;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PatchIsInBed extends ClassPatch
{
    public PatchIsInBed(ClassWriter writer)
    {
        super(writer);
        matchingMethods.add(new MethodToPatch("trySleep", "(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;"));
        matchingMethods.add(new MethodToPatch("a", "(Lcj;)Lwn$a;"));
    }

    @Override
    public MethodVisitor patchedVisitor(MethodVisitor parent)
    {
        return new PatchIsInBedVisitor(parent);
    }

    public class PatchIsInBedVisitor extends MethodVisitor
    {
        public PatchIsInBedVisitor(MethodVisitor visitor)
        {
            super(Opcodes.ASM4, visitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
        {
            System.out.println("VISITING THE DARN METHOD");
            if (desc.equals("()Z") && name.equals("isDaytime") || name.equals("w"))
            {
                super.visitInsn(Opcodes.POP);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "arc/sleep/SleepEventFactory", "canNotSleep", "(Lnet/minecraft/entity/player/EntityPlayer;)Z", false);
            }
            else
            {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }
}
