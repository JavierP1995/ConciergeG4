<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRecordsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('records', function (Blueprint $table) {
            $table->id();
            $table->char('type');
            $table->string('kinship');
            $table->date('entryDate');
            $table->date('departureDate');
            $table->string('comment');

            $table->biginteger('resident_id');
            $table->biginteger('visit_id');
            $table->biginteger('department_id');
            $table->timestamps();
            $table->foreign('resident_id')->references('id')->on('residents')
                ->onUpdate('cascade')
                ->onDelete('cascade');
            $table->foreign('visit_id')->references('id')->on('visits')
                ->onUpdate('cascade')
                ->onDelete('cascade');
            $table->foreign('department_id')->references('id')->on('departments')
                ->onUpdate('cascade')
                ->onDelete('cascade');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('records');
    }
}
